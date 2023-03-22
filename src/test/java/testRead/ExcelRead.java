package testRead;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.MyApp.model.Employee;

public class ExcelRead  {
	
	static  List<Employee> employees = new ArrayList<>();
    public static void ReadExcelFile (String FILE_NAME) {

        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            Row r; 
            int maxColomn = 0;
            String CellValue = "";
            DataFormatter formatter = new DataFormatter();
            
            System.out.println(" --------Resumé du fichier excel " + FILE_NAME + "---------- ");
            System.out.println(" ");
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                  
                while (cellIterator.hasNext()) {	
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellType() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }
                }
                System.out.println();
            } 
            System.out.println(" ");
            for (int i=0; i <= sheet.getLastRowNum(); i++) {              
                	r = sheet.getRow(i);
                	maxColomn = r.getLastCellNum();
                	Employee employe = new Employee();
                	employees.add(employe);
                	for (int j=0; j<maxColomn; j++) {
                	
                	CellValue = formatter.formatCellValue(sheet.getRow(i).getCell(j));
                		if (j==0) {
                			employe.setFirstName(CellValue);
                			System.out.println("employee " + i + " firstName: " + employe.getFirstName());
                		}
                		if (j==1) {
                			employe.setLastName(CellValue);
                			System.out.println("employee " + i + " LastName: " + employe.getLastName());
                		} 
                		if (j==2) {
                			employe.setEmail(CellValue);
                			System.out.println("employee " + i + " Email: " + employe.getEmail());
                		} 
                		System.out.println("-------------------- ");
                	}
                	
            }
            System.out.println(" ");
            System.out.println("Nombre de Ligne : " + sheet.getLastRowNum());
            System.out.println("Nombre de Colonne : " + maxColomn);
            System.out.println(" ");
            
            for(Object o:employees)  {
      		  System.out.println("employee " + o);  
      		  
      		} 
            workbook.close();
            excelFile.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}