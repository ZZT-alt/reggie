package com.zztqvq.dto;

import com.zztqvq.entity.Setmeal;
import com.zztqvq.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
