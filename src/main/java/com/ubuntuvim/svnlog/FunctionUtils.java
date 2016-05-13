package com.ubuntuvim.svnlog;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 检查提交的文件对应的是哪个功能的
 */
public class FunctionUtils {
	
	// Map<包名, 功能名>
	private Map<String, String> functionMap = new HashMap<String, String>();
	
	public FunctionUtils() {
		functionMap.put("/accountActivatio/", "账户激活");
		functionMap.put("/accountQuery/", "证券账户查询");
		functionMap.put("/acctCancel/", "账户销户");
		functionMap.put("/acctconstraint/", "账户约束");
		functionMap.put("/additionalImg/", "影像资料补齐");
		functionMap.put("/authorization/", "股转业务");
		functionMap.put("/bond/", "债券质押式报价回购业务");
		functionMap.put("/channels/", "委托方式处理");
		functionMap.put("/commission/", "设置资产账户佣金");
		functionMap.put("/common/", "通用业务");
		functionMap.put("/currencysecuritietransf/", "外币银证转账");
		functionMap.put("/customerOpen/", "开户");
		functionMap.put("/delisting/", "沪市两市退市");
		functionMap.put("/deposit/", "三方存管");
		functionMap.put("/electronicCaseSigned/", "电子签名");
		functionMap.put("/foreignCurrDeposit/", "外币开通");
		functionMap.put("/foreignCurrTakeOut/", "现金取款");
		functionMap.put("/fund/", "基金确权");
		functionMap.put("/fundDividendChange/", "场内基金分红方式变更");
		functionMap.put("/fundperiodquota/", "基金定期定额");
		functionMap.put("/gem/", "创业板开通、撤销");
		functionMap.put("/hk/", "证券账户港股");
		functionMap.put("/impdatachange/", "一码通");
		functionMap.put("/Investor/", "沪A债券合格投资者");
		functionMap.put("/newAccount/", "新增账户");
		functionMap.put("/nonKeyInfoChange/", "客户非关键信息");
		functionMap.put("/otcfundtransvenue/", "股权转让");
		functionMap.put("/otcTrade/", "OTC交易");
		functionMap.put("/outToInFundShare/", "场外基金转场内");
		functionMap.put("/pfis/", "专业投资者申请、撤销");
		functionMap.put("/preferredStock/", "上海市场优先股交易权限开通、注销");
		functionMap.put("/pwreset/", "密码修改");
		functionMap.put("/querydelivery/", "查询与交割");
		functionMap.put("/relation/", "关联关系维护办理");
		functionMap.put("/setquota/", "大额绿色通道维护");
		functionMap.put("/shanghaiRiskCaution/", "上海市场风险警示股票交易权限开通、撤销");
		functionMap.put("/shanghaistocktransferinvestors/", "上海市场优先股交易权限开通及注销");
		functionMap.put("/twonetsanddelis/", "两市退市整理期股票交易权限开通、撤销");
		functionMap.put("/venFundunitTurnOffsite/", "场内基金份额转场外");
		functionMap.put("/wholesale/", "大额取款申请");
	}
	
	public String getFunctionName(String filePath) {
		String busiName = "";
		Set<String> keys = this.functionMap.keySet();
		for (String key : keys) {
			if (filePath.contains(key)) {
				busiName = this.functionMap.get(key);
				break;
			}
		}

		return busiName;
	}
	
	public static void main(String[] args) {
		String filePath = "/4 Implement/ATOMBRANCH/MOBILE/src/main/java/com/szkingdom/opp/atom/business/accountActivatio/AcctOpenASHHandleProcess.java";
		FunctionUtils fu = new FunctionUtils();
		System.out.println(fu.getFunctionName(filePath));
	}
}
