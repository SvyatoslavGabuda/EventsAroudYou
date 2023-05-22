package it.epicode.eaw.image;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	@Autowired
	ImageRepo iRepo;

	public Image saveImage(MultipartFile file) {

		try {
			Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			Image iSaved = iRepo.save(image);
			iSaved.setUrl("http://localhost:8081/api/images/db/" + image.getId_img());
			iRepo.save(iSaved);

			return iRepo.save(iSaved);
		} catch (Exception e) {

			return new Image();
		}

	}
}
