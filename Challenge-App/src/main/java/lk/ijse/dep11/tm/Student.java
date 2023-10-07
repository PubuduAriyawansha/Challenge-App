package lk.ijse.dep11.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student implements Serializable {

    private String id;
    private String name;
    private String card;
    private String status;
}
