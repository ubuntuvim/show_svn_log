/*
 * ====================================================================
 * Copyright (c) 2004-2011 TMate Software Ltd.  All rights reserved.
 *
 * This software is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://svnkit.com/license.html
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 * ====================================================================
 */
package com.ubuntuvim.svnlog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.ubuntuvim.framework.model.SvnLog;
import com.ubuntuvim.framework.service.impl.SVNLogService;

public class SyncSVNLogHistoryToLocal2 {

	/**
	 * 获取svn的日志记录
	 * @param name 登录名
	 * @param password 登录svn的密码
	 * @param url svn的URL
	 * @param lastVersion 获取日志的开始版本号
	 * @return
	 */
    @SuppressWarnings("deprecation")
	public List<SvnLog> sync(String name, String password, String url, int startRevision) {
        /*
         * Default values:
         */
//    	String url = "https://10.7.86.68/svn/JAYZT/";
//        String name = "hanx";
//        String password = "hanx";
//        long startRevision = 0;
        long endRevision = -1;//HEAD (the latest) revision
        /*
         * Initializes the library (it must be done before ever using the
         * library itself)
         */
        setupLibrary();

        SVNRepository repository = null;
        
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException svne) {
            /*
             * Perhaps a malformed URL is the cause of this exception.
             */
            System.err
                    .println("error while creating an SVNRepository for the location '"
                            + url + "': " + svne.getMessage());
            System.exit(1);
        }

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);

        /*
         * Gets the latest revision number of the repository
         */
        try {
            endRevision = repository.getLatestRevision();
        } catch (SVNException svne) {
            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
            System.exit(1);
        }

        Collection<?> logEntries = null;
        try {
            logEntries = repository.log(new String[] {""}, null,
                    startRevision, endRevision, true, true);

        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '"
                    + url + "': " + svne.getMessage());
            System.exit(1);
        }
        
        FunctionUtils fu = new FunctionUtils();
        String filePath = "";
        List<SvnLog> list = new ArrayList<SvnLog>();
        SVNLogService logService = new SVNLogService();
        for (Iterator<?> entries = logEntries.iterator(); entries.hasNext();) {
        	
            /*
             * gets a next SVNLogEntry
             */
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
            /*
             * displaying all paths that were changed in that revision; cahnged
             * path information is represented by SVNLogEntryPath.
             */
            if (logEntry.getChangedPaths().size() > 0) {

              
                /*
                 * keys are changed paths
                 */
                Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();

                for (Iterator<?> changedPaths = changedPathsSet.iterator(); changedPaths
                        .hasNext();) {
                    /*
                     * obtains a next SVNLogEntryPath
                     */
                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                    
                    // 转换时间
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String dataStr = sdf.format(logEntry.getDate());
                    filePath = entryPath.getPath();
                    String busiName = fu.getFunctionName(filePath);
                    // 生成sql文件
//                    String sql = "insert into svn_log(changeFilePath, revision, author, logMsg, changeDate, opType)  " +
//                    		"values('"+filePath+"', '"+logEntry.getRevision()+"', '"+logEntry.getAuthor()+"', '"+logEntry.getMessage()+"', '"+dataStr+"', '"+entryPath.getType()+"');";
//                    contentToTxt("c:/svn_log.sql", sql);
                    SvnLog svnLog = new SvnLog();
                    svnLog.setRevision(logEntry.getRevision()+"");
                    svnLog.setAuthor(logEntry.getAuthor());
                    svnLog.setChangeDate(logEntry.getDate());
                    svnLog.setLogMsg(logEntry.getMessage());
                    svnLog.setOpType(entryPath.getType()+"");
                    svnLog.setChangeFilePath(entryPath.getPath());
                    svnLog.setFunctionName(busiName);
                    
                    try {
                    	System.out.println(svnLog);
						logService.add(svnLog);
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        }
        
        return list;
    }

	public static void contentToTxt(String filePath, String content) {  
		BufferedWriter fw = null;
		try {
			File file = new File(filePath);
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
			System.out.println(content);
			fw.append(content);
			fw.newLine();
			fw.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
    } 
	
	public static void main(String[] args) {
		String url = "https://10.7.86.68/svn/JAYZT/";
        String username = "hanx";
        String password = "hanx";
		System.out.println("=======================================");
		SyncSVNLogHistoryToLocal2 syn = new SyncSVNLogHistoryToLocal2();
		syn.sync(username, password, url, 0);
		System.out.println("======================2222222222222=================");
	}
	
	
    /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }
}
