package com.app.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	@Transactional
	public void saveUser(User u) {
		 repository.save(u);
	}

	@Override
	@Transactional
	public void updateUser(User u) {
         repository.save(u);
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
       repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public User getUserById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> getAllUsers() {
		return (List<User>) repository.findAll();
	}

	@Override
	@Transactional
	public boolean saveDataFromFile(CommonsMultipartFile file) {

		 boolean flag=false;
		 String extention=FilenameUtils.getExtension(file.getOriginalFilename());
		 
		 if(extention.equalsIgnoreCase("JSON")) {
			 flag=readDataFromJson(file);
		 } 
		 else if(extention.equalsIgnoreCase("CSV")) {
			 flag=readDataFromCSV(file);
		 }
		 else if(extention.equalsIgnoreCase("xls")|| extention.equalsIgnoreCase("xlsx")) {
			 flag=readDataFromExcel(file);
		 }
		 else  {
			 
		 }
		return flag;
	}


	private boolean readDataFromExcel(CommonsMultipartFile file) {
		Workbook workbook=getUserBook(file);
		Sheet sheet=workbook.getSheetAt(0);
		Iterator<Row> rows=sheet.iterator();
		rows.next();
		
		while(rows.hasNext()) {
			Row row=rows.next();
			User user=new User();
			
			if(row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING) {
				user.setFirstName(row.getCell(0).getStringCellValue());
			}
			if(row.getCell(1).getCellType()==Cell.CELL_TYPE_STRING) {
				user.setLastName(row.getCell(1).getStringCellValue());
			}
			if(row.getCell(2).getCellType()==Cell.CELL_TYPE_STRING) {
				user.setEmail(row.getCell(2).getStringCellValue());
			}
			if(row.getCell(3).getCellType()==Cell.CELL_TYPE_NUMERIC) {
				String phoneNumber=NumberToTextConverter.toText(row.getCell(3).getNumericCellValue());
				user.setPhoneNumber(phoneNumber);
			}
			else {
				user.setPhoneNumber(row.getCell(3).getStringCellValue());
			}
			
			user.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
		    repository.save(user);
		}
		return true;
	}

	private Workbook getUserBook(CommonsMultipartFile file) {
	    Workbook workbook=null;
	    String extention=FilenameUtils.getExtension(file.getOriginalFilename());
	    
	    try{
	    	if(extention.equalsIgnoreCase("xlsx")) {
	    		workbook=new XSSFWorkbook(file.getInputStream());
	    	}
	    	else{
	    		workbook=new HSSFWorkbook(file.getInputStream());
	    	}
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
		return workbook;
	}

	private boolean readDataFromCSV(CommonsMultipartFile file) {
		
		try {
			
			InputStreamReader reader=new InputStreamReader(file.getInputStream());
			 CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(1).build();
			 
			 List<String[]> rows=csvReader.readAll();
			 
			 for(String[] row:rows) {
				 User u=new User();
				 u.setFirstName(row[0]);
				 u.setLastName(row[1]);
				 u.setEmail(row[2]);
				 u.setPhoneNumber(row[3]);
				 u.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));;
				 repository.save(u);
			 }//for
			 return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean readDataFromJson(CommonsMultipartFile file) {
		
        try {
             //InputStream inputStream =file.getInputStream();
      		
        	 InputStream inputStream=file.getInputStream();
        	ObjectMapper mapper=new ObjectMapper();
      		
      		List<User> users=Arrays.asList(mapper.readValue(inputStream, User[].class));
			  
      		if(users !=null && users.size()>0) {
      			for(User user:users) {
      				user.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
      				repository.save(user);
      			}
      		}
      		return true;
		}//try
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}//catch
	}//method

}
