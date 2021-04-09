package updatecolums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class ProductionBrandsData implements Serializable {
    private int brand_id;
    private String brandName;
}