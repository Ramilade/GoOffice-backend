package com.example.goofficebackend.dto;

import com.example.goofficebackend.entity.Desk;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeskResponse {

        private int id;

        public DeskResponse(Desk d){
            this.id = d.getId();
        }
}
