package com.bwf.duobao.instance.fenlei;
/**
*@author Li gang
*@date  2016年6月6日上午2:43:54
*@declaration
*/
public class FenleiGoodsItem {
	private int code;
	private GoodsItems result;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public GoodsItems getResult() {
		return result;
	}
	public void setResult(GoodsItems result) {
		this.result = result;
	}
	public FenleiGoodsItem(int code, GoodsItems result) {
		super();
		this.code = code;
		this.result = result;
	}
	public FenleiGoodsItem() {
		super();
	}
}
