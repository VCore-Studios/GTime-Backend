package com.vblocks.gtime.service;

import com.vblocks.gtime.dto.detail.DetailInput;
import com.vblocks.gtime.entity.detail.Detail;
import com.vblocks.gtime.repository.DetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DetailService {

    private final DetailRepository detailRepository;

    public Detail createDetail(DetailInput detailInput) {
        Detail detail = new Detail();
        detail.setName(detailInput.getName());
        detail.setText(detailInput.getText());
        return detailRepository.save(detail);
    }

    public Detail editDetail(Long detailId, DetailInput detailInput) {
        Detail detail = detailRepository.findById(detailId).orElse(null);
        if (detail == null) return null;
        if (!detailInput.getName().isEmpty()) {
            detail.setName(detailInput.getName());
        }
        if (!detailInput.getText().isEmpty()) {
            detail.setText(detailInput.getText());
        }
        return detailRepository.save(detail);
    }

    public Boolean deleteDetail(Long detailId) {
        return detailRepository.deleteDetailById(detailId);
    }

    public List<Detail> getAllDetail() {
        return detailRepository.findAll();
    }
}
