/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoapp.apialbums.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "albums")
public class Albums {

    @Id
    @GeneratedValue
    private long id;
    private String albumId;
    private String userId; 
    private String name;
    private String description; 
}
