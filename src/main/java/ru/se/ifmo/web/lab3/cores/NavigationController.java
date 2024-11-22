package ru.se.ifmo.web.lab3.cores;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable {
    public String moveToStart() {
        return "index";
    }
    public String moveToMain() {
        return "main";
    }
}