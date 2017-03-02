package com.hn658.${projectName}.dictionary.dto;

import java.io.Serializable;

/**
 * 数据字典应用DTO
 */
public class DictionaryDTO implements Serializable {

	private static final long serialVersionUID = -614234711637534305L;
	// 数据value
	private String display;
	// 数据key
	private String value;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
