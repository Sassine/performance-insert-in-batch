package dev.sassine.perfomance.insert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqProduct")
    @SequenceGenerator(name = "seqProduct", sequenceName = "seqProductGen", initialValue = 1)
    private Long id;
    private String name;

}
