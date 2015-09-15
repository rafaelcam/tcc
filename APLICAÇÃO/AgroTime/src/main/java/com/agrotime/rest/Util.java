package com.agrotime.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agrotime.entidades.Pessoa;

public class Util {
	 
    private static Util util = new Util();
    private List<Pessoa> lPessoa = new ArrayList<Pessoa>();
 
    private void init() {
 
    Pessoa pessoa = null;
    for (int i = 0; i < 10; i++) {
        pessoa = new Pessoa(i, "Clayton" + i, "email@gmail.com", new Date(), new Date(), i * 3);
        lPessoa.add(pessoa);
    }
 
    }
 
    private Util() {
        init();
    }
 
    public static Util getInstance() {
 
        if (util == null) {
            util = new Util();
        }
 
        return util;
 
    }
 
    public List<Pessoa> getlPessoa() {
        return lPessoa;
    }
 
    public void setlPessoa(List<Pessoa> lPessoa) {
        this.lPessoa = lPessoa;
    }
 
    public int getId(){
 
        return lPessoa.size();
 
    }
}