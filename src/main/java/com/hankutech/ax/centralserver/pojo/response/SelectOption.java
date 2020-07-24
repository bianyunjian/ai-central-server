package com.hankutech.ax.centralserver.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "界面表单下拉选选项option映射")
@Data
public class SelectOption {

    @Schema(description = "下拉选选项值")
    private String value;

    @Schema(description = "下拉选选项标签")
    private String label;

    @Schema(description = "下拉选选项是否禁用")
    private Boolean disabled = false;

    public SelectOption(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public SelectOption(String value, String label, Boolean disabled) {
        this.value = value;
        this.label = label;
        this.disabled = disabled;
    }
}
