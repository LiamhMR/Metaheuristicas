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
            File file = new File("Investigacion/src/ExcelInvOp.xlsx");   //Crear instancia de archivo
            FileInputStream fis = new FileInputStream(file);   
            //Abrir libro EXCEL
            XSSFWorkbook wb = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wb.getSheetAt(0);     //Crear una hoja
            Iterator<Row> itr = sheet.iterator();    //Iterar sobre la hoja
            int nrow=0;
            while (itr.hasNext())                 
            {   
                Row row = itr.next();  
                int id=0;
                double costo=0;
                int vecinos[] = {};
                int ncol=0;
                Iterator<Cell> cellIterator = row.cellIterator();   //Columna
                while (cellIterator.hasNext())   
                {  
                    Cell cell = cellIterator.next();  
                    ncol++;
                    switch (cell.getCellType())               
                    {  
                        case Cell.CELL_TYPE_STRING:    //Si es texto se asume que es la columna de nombres, se omite 
                        //System.out.print(cell.getStringCellValue() + "\t\t\t"); //Nombre columna 
                        break;  

                        //SI ES NUMERICO SE ANALIZA EL VALOR
                        case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type 
                            //System.out.print("vc: "+cell.getNumericCellValue() + "\t\t\t"); 
                            //Columna | 1:Costo | 2:Id | 3 al 10: los vecinos 
                            switch(ncol){
                                case 1:
                                    costo=cell.getNumericCellValue();
                                break;
                                case 2:
                                    id=(int)cell.getNumericCellValue();
                                break;
                                case 3,4,5,6,7,8,9,10:
                                    if(cell.getNumericCellValue()!=0){
                                        int auxNeighbour[]= new int[vecinos.length];
                                        for (int i=0;i<auxNeighbour.length;i++){
                                            auxNeighbour[i]=vecinos[i];
                                        }                      
                                        vecinos=new int[ncol+1];
                                        for (int i=0;i<auxNeighbour.length;i++){
                                            vecinos[i]=auxNeighbour[i];
                                        }
                                        vecinos[ncol-3]=(int)cell.getNumericCellValue();
                                    }  
                                break;
                            }
                        break;  
                    }
                }  
                if (id!=0){
                    //Añade el nodo
                    ArrNode[nrow]=new Nodos(id,costo,vecinos);
                    nrow++;
                }
            }
            wb.close();  //Cerrar libro excel
        }  
        catch(Exception e)  
        {  
            //e.printStackTrace();  
        }
    }  

    /**
     * @return Retorna una instancia nueva con los datos de los nodos.
     */
    public Nodos[] ReturnArr(){
        Nodos NewArr[]=new Nodos[36];
        for (int i=0;i<ArrNode.length;i++){  
            int id=ArrNode[i].Id;
            double costo=ArrNode[i].Costo;
            int vecinos[] = ArrNode[i].Vecinos;
            NewArr[i]=new Nodos(id, costo, vecinos);
        }
        return NewArr;
    }
}  
