package com.mall.jelly.base;

import com.mall.jelly.constants.Constants;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.utils.Global;
import lombok.Data;

/**
 *
 * @description: 微服务接口统一返回码
 */
@Data
public class BaseResponse<T> {

	/**
	 * 返回码
	 */
	private Integer code= Constants.HTTP_RES_CODE_500;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 返回
	 */
	private T data;

	public BaseResponse() {
		this.setLocal(ResponseCodeEnum.SUCCESS);
		//销毁缓存数据
		Global.remove();
	}

	public BaseResponse( T data) {
		super();
		this.setLocal(ResponseCodeEnum.SUCCESS);
		this.data = data;
		Global.remove();
	}

	public BaseResponse(Integer code, String msg, T data) {
		super();
		this.setLocal(ResponseCodeEnum.SUCCESS);
		this.code = code;
		this.msg = msg;
		this.data = data;
		Global.remove();
	}


	public void setLocal(ResponseCodeEnum responseCodeEnum, Object... params) {
		switch (Global.get("locale") + "") {
			case "en":
				this.setMsg(responseCodeEnum.getDescEN());
				break;
			case "ja_JP":
				this.setMsg(responseCodeEnum.getDescJA());
				break;
			case "zh_HK":
				this.setMsg(responseCodeEnum.getDescTW());
				break;
			default:
				this.setMsg(responseCodeEnum.getDesc());
				break;
		}

		this.setCode(Integer.parseInt(responseCodeEnum.getStatus()));
		if (params != null && params.length > 0) {
			this.setMsg(String.format(this.getMsg(), params));
		}
	}

}
