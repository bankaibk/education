package com.hdu.eduservice.service;

import com.hdu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
