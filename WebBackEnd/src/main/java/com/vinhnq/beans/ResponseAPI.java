package com.vinhnq.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseAPI<T> {
	private int status;
	private String message;
	private T data;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private Integer draw = 0;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private Integer recordsTotal = 0;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private Integer recordsFiltered = 0;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long dataId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String hostUrl;

	public ResponseAPI() {
	}

	public ResponseAPI(int status, T data) {
		this.status = status;
		this.data = data;
	}
	public ResponseAPI(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public ResponseAPI(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	@Override
	public String toString() {
		return "ResponseAPI{" +
				"status=" + status +
				", message='" + message + '\'' +
				", draw=" + draw +
				", recordsTotal=" + recordsTotal +
				", recordsFiltered=" + recordsFiltered +
				", data=" + data +
				'}';
	}
}
