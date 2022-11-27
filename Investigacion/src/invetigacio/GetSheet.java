package invetigacio;

import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  



public class GetSheet  
{  
    static Nodos ArrNode[]=new Nodos[36];

    GetSheet()   
    {  
        try  
        {  
            File file = new File("Investigacion/src/ExcelInvOp.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  

            while (itr.hasNext())                 
            {   
                int id=0;
                float costo=0;
                int vecinos[] = {};
                Row row = itr.next();  
                int nrow=0;
                int ncol=0;
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                while (cellIterator.hasNext())   
                {  
                    Cell cell = cellIterator.next();  
                    ncol++;
                    System.out.print(ncol+" ");
                    switch (cell.getCellType())               
                    {  
                        case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
                        //System.out.print(cell.getStringCellValue() + "\t\t\t"); //Nombre columna 
                        break;  

                        //SI ES NUMERICO SE ANALIZA EL VALOR
                        case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type 
                            switch(ncol){
                                case 1:
                                    costo=Float.parseFloat(cell.getStringCellValue());
                                break;
                                case 2,3,4,5,6,7,8,9:
                                    vecinos[ncol-2]=Integer.parseInt(cell.getStringCellValue());
                                break;
                                case 10:
                                    id=Integer.parseInt(cell.getStringCellValue());
                                break;
                            }
                            //System.out.print(cell.getNumericCellValue() + "\t\t\t");  
                            break;  
                        default:  
                    }
                    ArrNode[nrow]=new Nodos(id,costo,vecinos);
                    nrow++;
                }  
                System.out.println("");  
            }
            wb.close();  //closed fix
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }
    }  

    public Nodos[] ReturnArr(){
        return ArrNode;
    }
}  
