/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoapp.apialbums.service;

import com.photoapp.apialbums.model.Albums;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlbumsServiceImpl implements AlbumsService {

    @Override
    public List<Albums> getAlbums(Long id) {
        List<Albums> returnValue = new ArrayList<>();

        Albums albumEntity = new Albums();
        // albumEntity.setUserId(id);
        albumEntity.setAlbumId("album1Id");
        albumEntity.setDescription("album 1 description");
        albumEntity.setId(id);
        albumEntity.setName("album 1 name");

        Albums albumEntity2 = new Albums();
        // albumEntity2.setUserId(userId);
        albumEntity2.setAlbumId("album2Id");
        albumEntity2.setDescription("album 2 description");
        albumEntity2.setId(id);
        albumEntity2.setName("album 2 name");
        
        returnValue.add(albumEntity);
        returnValue.add(albumEntity2);
        
        return returnValue;
    }
    
}
