package com.stefanini.librarybackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historyId_generator")
    @SequenceGenerator(name = "historyId_generator", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "actionName")
    private String actionName;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    History(int id, String actionName, Date date) {
        this.id = id;
        this.actionName = actionName;
        this.date = date;
    }

    History(String actionName, Date date) {
        this.actionName = actionName;
        this.date = date;
    }
}