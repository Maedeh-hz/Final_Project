package org.example.utility;

import org.example.record.UpdatingDescription;
import org.example.record.UpdatingWage;

import java.util.Objects;

public class UpdatingMethods {

    public static UpdatingDescription getDescription(){
        boolean flag = true;
        String description;
        do {
            description = Menu.getString();
            if (description.isEmpty())
                flag = false;
        } while (!description.isEmpty());
        return new UpdatingDescription(description, flag);
    }

    public static UpdatingWage getWage(Double base){
        boolean flag = true;
        Double wage;
        do {
            wage = Menu.getDouble();
            if (wage == 0)
                flag=false;
        } while (!Objects.equals(wage, base)&& wage != 0);
        return new UpdatingWage(wage, flag);
    }


}
