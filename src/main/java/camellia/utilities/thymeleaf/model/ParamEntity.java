package camellia.utilities.thymeleaf.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Datetime: 2024/11/7上午9:41
 * @author: Camellia.xioahua
 */
@Data
public class ParamEntity implements Serializable {

    private static final long serialVersionUID = 2971651722456178553L;

    // 基本类型
    private String name;
    private String value;

    // 数字类型
    private Integer intValue;
    private Long longValue;
    private BigDecimal decimalValue;

    // 布尔类型
    private Boolean isActive;

    // 日期时间类型
    private LocalDate dateValue;
    private LocalDateTime dateTimeValue;

    // 列表和数组类型
    private String[] tags;
    private List<String> interests;

    // 嵌套对象类型
    private ParamEntity relatedEntity;

    // 自定义格式化的值，例如金额格式
    public String getFormattedDecimalValue() {
        if (decimalValue != null) {
            return String.format("$%,.2f", decimalValue);
        }
        return null;
    }

}
