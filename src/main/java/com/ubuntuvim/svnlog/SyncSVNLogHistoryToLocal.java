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

/*
 * The following example program demonstrates how you can use SVNRepository to
 * obtain a history for a range of revisions including (for each revision): all
 * changed paths, log message, the author of the commit, the timestamp when the 
 * commit was made. It is similar to the "svn log" command supported by the 
 * Subversion client library.
 * 
 * As an example here's a part of one of the program layouts (for the default
 * values):
 * 
 * ---------------------------------------------
 * revision: 1240
 * author: alex
 * date: Tue Aug 02 19:52:49 NOVST 2005
 * log message: 0.9.0 is now trunk
 *
 * changed paths:
 *  A  /trunk (from /branches/0.9.0 revision 1239)
 * ---------------------------------------------
 * revision: 1263
 * author: sa
 * date: Wed Aug 03 21:19:55 NOVST 2005
 * log message: updated examples, javadoc files
 *
 * changed paths:
 *  M  /trunk/doc/javadoc-files/javadoc.css
 *  M  /trunk/doc/javadoc-files/overview.html
 *  M  /trunk/doc/examples/src/org/tmatesoft/svn/examples/wc/StatusHandler.java
 * ...
 * 
 */
public class SyncSVNLogHistoryToLocal {

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
            /*
             * Creates an instance of SVNRepository to work with the repository.
             * All user's requests to the repository are relative to the
             * repository location used to create this SVNRepository.
             * SVNURL is a wrapper for URL strings that refer to repository locations.
             */
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

        /*
         * User's authentication information (name/password) is provided via  an 
         * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
         * authentication manager given user's name and password.
         * 
         * Default authentication manager first attempts to use provided user name 
         * and password and then falls back to the credentials stored in the 
         * default Subversion credentials storage that is located in Subversion 
         * configuration area. If you'd like to use provided user name and password 
         * only you may use BasicAuthenticationManager class instead of default 
         * authentication manager:
         * 
         *  authManager = new BasicAuthenticationsManager(userName, userPassword);
         *  
         * You may also skip this point - anonymous access will be used. 
         */
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
            /*
             * Collects SVNLogEntry objects for all revisions in the range
             * defined by its start and end points [startRevision, endRevision].
             * For each revision commit information is represented by
             * SVNLogEntry.
             * 
             * the 1st parameter (targetPaths - an array of path strings) is set
             * when restricting the [startRevision, endRevision] range to only
             * those revisions when the paths in targetPaths were changed.
             * 
             * the 2nd parameter if non-null - is a user's Collection that will
             * be filled up with found SVNLogEntry objects; it's just another
             * way to reach the scope.
             * 
             * startRevision, endRevision - to define a range of revisions you are
             * interested in; by default in this program - startRevision=0, endRevision=
             * the latest (HEAD) revision of the repository.
             * 
             * the 5th parameter - a boolean flag changedPath - if true then for
             * each revision a corresponding SVNLogEntry will contain a map of
             * all paths which were changed in that revision.
             * 
             * the 6th parameter - a boolean flag strictNode - if false and a
             * changed path is a copy (branch) of an existing one in the repository
             * then the history for its origin will be traversed; it means the 
             * history of changes of the target URL (and all that there's in that 
             * URL) will include the history of the origin path(s).
             * Otherwise if strictNode is true then the origin path history won't be
             * included.
             * 
             * The return value is a Collection filled up with SVNLogEntry Objects.
             */
            logEntries = repository.log(new String[] {""}, null,
                    startRevision, endRevision, true, true);

        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '"
                    + url + "': " + svne.getMessage());
            System.exit(1);
        }
        List<SvnLog> list = new ArrayList<SvnLog>();
        FunctionUtils fu = new FunctionUtils();
        String filePath = "";
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
                    
//                    char entrytype = entryPath.getType();
//                    String typeCN = "";
//                    if ("M".equals(String.valueOf(entrytype))) {
//                        typeCN = "修改";
//                    } else if ("A".equals(String.valueOf(entrytype))) {
//                        typeCN = "新增";
//                    } else if ("D".equals(String.valueOf(entrytype))) {
//                        typeCN = "删除";
//                    } else if("C".equals(String.valueOf(entrytype))){
//                    	typeCN = "冲突";
//                    } else if("G".equals(String.valueOf(entrytype))){
//                    	typeCN = "本地文件与服务器的进行合并";
//                    } else if("U".equals(String.valueOf(entrytype))){
//                    	typeCN = "从服务器上更新";
//                    } else if("R".equals(String.valueOf(entrytype))){
//                    	typeCN = "从服务器上替换";
//                    }
                    
                    /*
                     * SVNLogEntryPath.getPath returns the changed path itself;
                     * 
                     * SVNLogEntryPath.getType returns a charecter describing
                     * how the path was changed ('A' - added, 'D' - deleted or
                     * 'M' - modified);
                     * 
                     * If the path was copied from another one (branched) then
                     * SVNLogEntryPath.getCopyPath &
                     * SVNLogEntryPath.getCopyRevision tells where it was copied
                     * from and what revision the origin path was at.
                     */
//                    System.out.println(" "
//                            + typeCN
//                            + "	"
//                            + entryPath.getPath()
//                            + ((entryPath.getCopyPath() != null) ? " (from "
//                                    + entryPath.getCopyPath() + " revision "
//                                    + entryPath.getCopyRevision() + ")" : ""));

                    filePath = entryPath.getPath();
                    String busiName = fu.getFunctionName(filePath);
                    //  冗余一些数据
                    SvnLog svnLog = new SvnLog();
                    svnLog.setRevision(logEntry.getRevision()+"");
                    svnLog.setAuthor(logEntry.getAuthor());
                    svnLog.setChangeDate(logEntry.getDate());
                    svnLog.setLogMsg(logEntry.getMessage());
                    svnLog.setOpType(entryPath.getType()+"");
                    svnLog.setChangeFilePath(entryPath.getPath());
                    svnLog.setFunctionName(busiName);
                    
                    list.add(svnLog);
                }
            }
        }
        
        return list;
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
