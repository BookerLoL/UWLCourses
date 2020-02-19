/* SparseMatrix.java
 *
 * Implementation of a SparseMatrix using generics
 *
 * Author: Ethan Booker
 * 
 * The SparseMatrix class implements various methods
 * that would be suitable for relative use. Through 
 * the use of a Linked List in order to structure a
 * matrix-like SparseMatrix 
 *
 */
/**
 * Necessary import to use the Function apply 
 */
import java.util.function.Function;
/**
 * Necessary import to use the BiFunction apply
 */
import java.util.function.BiFunction;

/**
 *  A generic sparse two-dimensional matrix with configurable default
 *  entry.
 * @param <Entry> the type of element contained in this matrix
 * 
 * @author Ethan Booker
 * @since 12/13/2017
 * @version 1.0
 */
public class SparseMatrix<Entry> 
{
    /**
     * A linked list that allows for a representation of a matrix  
     * 
     * @author Ethan Booker
     * @since 12/13/2017
     * @version 1.0
     */
     public class List
     {
         /** 
          * Holds the entry data
          */
          Entry data;
         /**
          * Holds the "row index"
          */
          long rowIdx;
         /** 
          * Holds the "column index"
          */
          long colIdx;
         /**
          * Reference to the next Row Node
          */
          List nextRow;
         /**
          * Reference to the next Column Node
          */
          List nextCol; 
          
         /**
          * Default constructor
          * 
          * @param Entry the data to hold
          * @param row the Node row index
          * @param column the Node column index
          */
         public List(Entry data, long row, long column)
         {
             this.data = data;
             rowIdx = row;
             colIdx = column;
              
             //Initialized the nextRow/Col to null right away
             nextRow = null; 
             nextCol = null; 
         }
          
         /**
          * Getter method to get the data
          * 
          * @Entry the data the Node holds
          */
         public Entry getData()
         {  
             return data;
         }
            
         /**
          * Getter method to get reference to next Row
          * 
          * @List reference to next Row
          */
         public List getNextRow()
         {
             return nextRow;
         }
          
         /**
          * Getter method to get reference to Next Column
          * 
          * @List reference to next Column
          */
         public List getNextCol()
         {
             return nextCol;
         }
          
         /**
          * Mutator to change the refernce of the current Node's Next Row
          * 
          * @param List desired new nextRow reference
          */
         public void setNextRow(List nextRow)
         {
             this.nextRow = nextRow;
         }
          
         /**
          * Mutator to change the refernce of the current Node's Next Column
          * 
          * @param List desired new nextCol reference
          */
         public void setNextCol(List nextCol)
         {
             this.nextCol = nextCol;
         }
    }
    
     /**
      * The desired default value
      */ 
     private final Entry dft;
     
     /**
      * The head of the SparseArray list
      */
     private List listHolder;
     
     /**
      * Keeps track of the declared Row
      */
     private final long maxRow;
     
     /**
      * Keeps track of the declared Column
      */
     private final long maxCol;
     
     /**
      * Keeps track of the unique values that differ from the default value
      */
     private int entries;
     
     


      
        
      /**
       * Constructs SparseMatrix of given row and column capacity
       * and default value of null
       *
       * @param rows the number of rows present in the matrix.
       * @param columns the number of columns present in the matrix.
       */
      public SparseMatrix(final long rows, final long columns) 
      {
          this.dft = null;
          maxRow = rows;
          maxCol = columns;
          entries = 0; 

      }
    
      /**
       * Constrcuts SparseMatrix of given row and column capacity 
       * and desired default value 
       *
       * @param rows the number of rows present in the matrix.
       * @param columns the number of columns present in the matrix.
       * @param dft the initial Entrys at every pairs of indices into
       * the matrix.
       */
      public SparseMatrix(final long rows, final long columns, final Entry dft) 
      {
          this.dft = dft;
          maxRow = rows;
          maxCol = columns;
          entries = 0; 

      }
    
      /**
       * Returns the default value
       * 
       * @return Entry default value
       */
      public Entry getDefaultEntry() 
      {
          return dft;
      }
    
      /**
       * Return the number of declared columns in the matrix.
       * 
       * @return long the SparseArray's row capacity
       */
      public long getDeclaredRows() 
      {
          return maxRow;
      }
    
      /**
       * Return the number of declared column in the matrix.
       * 
       * @return long the SparseArray's column capacity
       */
      public long getDeclaredColumns()
      {
          return maxCol;
      }
    
      /**
       * Returns the total number of non-default values stored in the
       * matrix.
       * 
       * @return int The unique values of the SparseArray
       */
      public int getStoredEntries() 
      {
          return entries;
      }
    
      /**
       * Returns the value stored at a particular pair of indices in this
       * matrix.
       * 
       * @param long The desired column in the matrix
       * @param long The desired row in the matrix
       * @throws ArrayIndexOutOfBoundsException if out of paramets of the SparseArray row and column
       * @return Entry the data held in the matrix
       */
      public Entry get(final long row, final long column)
      {
          //Checks if either the row or column is equal to or greater than the declared values and if less than 0
          if(row < 0 || row >= getDeclaredRows())
          {
              throw new ArrayIndexOutOfBoundsException("Invalid row index");
          }
          else if(column < 0 || column >= getDeclaredColumns())
          {
              throw new ArrayIndexOutOfBoundsException("Invalid column index");
          }
          else if(!isSet(row, column))
          {
              return getDefaultEntry();
          }
          else
          {
              return getSpecRow(row, getSpecColumn(column)).getData();
          }
      }
      
      public String toString()
      {
          String s = "";
          List temp = listHolder;
          List temp2 = listHolder;
		  
          while(temp2 != null)
          {
              while(temp != null)
              {
                  s += "row: " + temp.rowIdx + "\t" + "col: " + temp.colIdx + "\t" + temp.data + "\n";
                  temp = temp.getNextRow();
              }
              temp = temp2.getNextCol();
              temp2 = temp2.getNextCol();
          }
			
          return s;
      }
    
      /**
       * Sets the desired matrix position with given value
       * 
       * @param value the value to be stored
       * @param long row of the matrix
       * @param long column of the matrix
       * @throws ArrayIndexOutOfBoundsException if out of paramets of the SparseArray row and column
       */
      public void set(final long row, final long column, final Entry valus) 
      {
          //Checks if either the row or column is equal to or greater than the declared values and if less than 0
          if(row < 0 || row >= getDeclaredRows())
          {
              throw new ArrayIndexOutOfBoundsException("Row is not in the SparseArray: " + row);
          }
          else if(column < 0 || column >= getDeclaredColumns())
          {
              throw new ArrayIndexOutOfBoundsException("Column is not in the SparseArray: " + column);
          }
          
          //Checks if the matrix is empty
          if(entriesIsZero())
          {
              //Checks if the value being set isn't default
              if((valus == null && getDefaultEntry() == null) || (valus != null && valus.equals(getDefaultEntry())))
              {
                  return;
              }
              //Add to the head of the list
              else
              {
                  listHolder = new List(valus, row, column);
                  entries++;
              }
          }
          //The list is not empty
          else
          {
              //Checks if the desired node exists
              if(isSet(row, column))
              {
                  //The value is default, thus changing an existing node to default is unsetting
                  if((valus == null && getDefaultEntry() == null) || (valus != null && valus.equals(getDefaultEntry())))
                  {
                      unset(row, column);
                  }
                  //Different value, just changing value
                  else
                  {
                      List temp = getSpecRow(row, getSpecColumn(column));
                      temp.data = valus;
                  }
              }
              //Node doesn't exist
              else
              {
                  //Time to check through the columns and ensure the valus isn't default 
                  if((valus == null && getDefaultEntry() == null) || (valus != null && valus.equals(getDefaultEntry())))
                  {
                      return;
                  }
                  //Finding column position
                  else
                  {
                      entries++;
                      if(getSpecColumn(column) == null)
                      {
                          List temp = listHolder;
                          
                          while(temp.colIdx < column && temp.getNextCol() != null)
                          {
                              temp = temp.getNextCol();
                          }
                          
						  if(temp.getNextCol() == null && column > temp.colIdx)
						  {
						  	temp.setNextCol(new List(valus, row, column));	
						  }
						  else if(column < listHolder.colIdx)
						  {
							 List newHead = new List(valus, row, column);
							 newHead.setNextCol(listHolder);
							 listHolder = newHead;
						  }
						  else
						  {
							List prev = getPrevCol(column);
							prev.setNextCol(new List(valus, row, column)); 
							prev.getNextCol().setNextCol(temp);
						  }
                      }
                      else
                      {
                          List exist = getSpecRow(row, getSpecColumn(column));
                          List cRow = getSpecColumn(column);
                          if(exist == null)
                          {
                              while(cRow.rowIdx < row && cRow.getNextRow() != null)
                              {
                                  cRow = cRow.getNextRow();
                              }
                              
                              if(cRow.getNextRow() == null && row > cRow.rowIdx)
                              {
                                  System.out.println("row: " + row);
                                  System.out.println("col: " + column);
                                  cRow.setNextRow(new List(valus, row, column));
                              }
                              else if(cRow == listHolder)
                              {
                                  List nextCol = listHolder.getNextCol();
                                  List temp = listHolder;
                                  temp.setNextCol(null);
                                  List newHead = new List(valus, row, column);
                                  newHead.setNextCol(nextCol);
                                  newHead.setNextRow(temp);
                                  listHolder = newHead;
                              }
                              else if(row < getSpecColumn(column).rowIdx)
                              {
                                  List keepCol = getSpecColumn(column); 
                                  List prev = getPrevCol(column);
                                  List next = keepCol.getNextCol();
                                  prev.setNextCol(new List(valus, row, column));
                                  prev.getNextCol().setNextCol(next);
                                  prev.getNextCol().setNextRow(keepCol);
                              }
                              else
                              {
                                  List next = cRow.getNextRow();
                                  cRow.setNextRow(new List(valus, row, column));
                                  cRow.getNextRow().setNextRow(next);
                              }
                          }
                      }
                  }
                }
            }
        }
       
      /**
       * Checks if desired matrix position contains a unique value
       * 
       * @long row the matrix row
       * @long column the matrix column
       * @return boolean retturn depends if the matrix position contains a unique value
       */
      public boolean isSet(final long row, final long column) 
      {
          //Checks for invalid position or if the matrix even contains a unique value at that point
          if(row < 0 || row >= maxRow || column < 0 || column >= maxCol || !contains(row, column))
          {
              return false;
          }
          
          //default value
          return true;
      }
    
      /**
       * Clears any unique value back to default 
       * No effects if the value at that position is default
       * 
       * @long row the matrix row
       * @long column the matrix column
       * @throws ArrayIndexOutOfBoundException if the row or column tries to access invalid matrix positions
       */
      public void unset(final long row, final long column) 
      {
          //Checking the parameter position to see if it is invalid
          if(row < 0 || row >= maxRow)
          {
              throw new ArrayIndexOutOfBoundsException("Row is out of bounds");
          }
          else if(column < 0 || column >= maxCol)
          {
              throw new ArrayIndexOutOfBoundsException("Column is out of bounds");
          }
          //Checks if the list even contains a unique value at that position
          else if(!isSet(row, column))
          {
              return;
          }
          
          //If size is one
          if(entries == 1)
          {
              listHolder = null;
              entries--;
          }
          else 
          {
              entries--;
              //Finds the column
              List temp = getSpecColumn(column);
              //If column and row (1st row) are the same
              if(temp.rowIdx == row && temp.colIdx == column)
              {
                  //If the head node
                  if(temp == listHolder)
                  {
					  //Has a row 
                      if(temp.getNextCol() == null && temp.getNextRow() != null)
                      {
                          listHolder = temp.getNextRow();
                      }
                      else
                      {
                          //No row on top
                          listHolder = temp.getNextCol();
				
                      }
                  }
                  //Checks if node at end of list
                  else if(temp.getNextCol() == null)
                  {
                      //Checks if end list has a row
                      if(temp.getNextRow() == null)
                      {
                          List prev = getPrevCol(column);
                          prev.setNextCol(null);
				
                      }
                      else
                      {
                          //Shifts list if row is found 
                          List prev = getPrevCol(column);
                          List newCol = temp.getNextRow();
                          prev.setNextCol(newCol);
                      }
                  }
                  //Found in the middle of the column
                  else if(temp.getNextCol() != null)
                  {
                      //Found with a row
                      if(temp.getNextRow() == null)
                      {
                          List prev = getPrevCol(column);
                          prev.setNextCol(temp.getNextCol());
						
                      }
                      else
                      {
                          List prev = getPrevCol(column);
                          List after = temp.getNextCol();
                          prev.setNextCol(temp.getNextRow());
                          prev.getNextCol().setNextCol(after);
                      }
                  }
              }
              else
              {
                  List tempR = getSpecRow(row, temp);
                  
                  if(tempR.getNextRow() == null)
                  {
                      List tRow = getPrevRow(row, temp);
                      tRow.setNextRow(null);
                      
                  }
                  else
                  {
                      List prevRow = getPrevRow(row, temp);
                      prevRow.setNextRow(tempR.getNextRow());
                  }
              }
          }
      }

	  private List getListHolder()
	  {
		return listHolder;
	  }
      
    
      /**
       * Transform into a new SparseMatrix using the given function and current Matrix
       * 
       * @param Function<Entry, Result> The given function to use apply 
       * @return <Result> SparseMatrix<Result> The transformed SparseMatrix
       */
      public <Result> SparseMatrix<Result> map(final Function<Entry,Result> function) 
      {
          //Gets the applied default value
          Result dft = function.apply(this.getDefaultEntry());
          
          //Creates the applied Sparse Matrix
          SparseMatrix<Result> applied = new SparseMatrix<>(this.getDeclaredRows(), this.getDeclaredColumns(), dft);
          
          //Iterates through the unique list
          List column = listHolder;
          List row = listHolder;
          Result data;
          //Keeps going through all the columns
          while(column != null)
          {
              //Checks all the row nodes
              while(row != null)
              {
                  //Gets the applied function data
                  data = function.apply(get(row.rowIdx, row.colIdx));
                  
                  applied.set(row.rowIdx, row.colIdx, data);
                  //Shifts row reference
                  row = row.getNextRow();
              }
              //Moves row onto to the next column row
              row = column.getNextCol();
              //Shifts column
              column = column.getNextCol();
          }
          
          return applied;
      }
    
      /**
       * Transform into a new SparseMatrix using the given BiFunction,m
       * current Matrix, and additional matrix
       * 
       * @param BiFunction<Entry, Entry2, Result> The given function to use apply 
       * @param SparseMatrix<Entry2> the other necessary matrix to manipulate
       * @return <Entry2, Result> SparseMatrix<Result> The transformed SparseMatrix using apply
       */
      public <Entry2,Result> SparseMatrix<Result>
          combine(final BiFunction<Entry,Entry2,Result> function,
                  final SparseMatrix<Entry2> that) 
      {
           //Gets the applied default value
          Result dft = function.apply(this.getDefaultEntry(), that.getDefaultEntry());
          //Creates the applied Sparse Matrix
          SparseMatrix<Result> applied = new SparseMatrix<>(getDeclaredRows(), getDeclaredColumns(), dft);
          //Iterates through the unique list
          List column = listHolder;
          List row = listHolder;
          Result data;
          
          //Keeps going through all the columns
          while(column != null)
          {
              //Checks all the row nodes
              while(row != null)
              {
                  //Gets the applied function data
                  data = function.apply(this.get(row.rowIdx, row.colIdx), that.get(row.rowIdx, row.colIdx));
                  applied.set(row.rowIdx, row.colIdx, data);
                  //Shifts row reference
                  row = row.getNextRow();
              }
              row = column.getNextCol();
              column = column.getNextCol();
          }
          
          return applied;
      }
    
      /**
       *  For two matrices of numbers, calculate the sum of the matrices,
       *  that is, by adding the pairs of numbers at the same position at
       *  the two matrices.
       *  
       *  Note: The default value should be the sum of the two matrix default value
       * @param array1 the first matrix of numbers
       * @param array2 the second matrix of numbers. This method will
       * @throws IllegalArgumentException if the two matrices differ in either
       * the number of rows or the number of columns
       * @return SparseMatrix<Double> the dotproduct of a SparseMatrix
       */
      public static SparseMatrix<Double>
          matrixAdd(final SparseMatrix<Double> matrix1, final SparseMatrix<Double> matrix2) 
      {
          //Checks if the rows and columns are the same
          if(matrix1.getDeclaredRows() != matrix2.getDeclaredRows())
          {
              throw new IllegalArgumentException("Rows don't match: " + matrix1.getDeclaredRows() + " vs " + matrix2.getDeclaredRows());
          }
          else if(matrix1.getDeclaredColumns() != matrix2.getDeclaredColumns())
          {
              throw new IllegalArgumentException("Columns don't match: " + matrix1.getDeclaredColumns() + " vs " + matrix2.getDeclaredColumns());
          }
          
          //Creating the default value from both matrix default summed together
          Double dft = matrix1.getDefaultEntry() + matrix2.getDefaultEntry();
          
          
          //Creates the new SparseMatrix
          SparseMatrix<Double> matrixSum = new SparseMatrix<>(matrix1.getDeclaredRows(), matrix1.getDeclaredColumns(), dft);
          
          //Iterates through the list and applies the dot product and assigns the value to correct location of new SparseArray
          for(long row = 0; row < matrix1.getDeclaredRows(); row++)
          {
              for(long col = 0; col < matrix1.getDeclaredColumns(); col++)
              {
                  matrixSum.set(row, col, (matrix1.get(row, col) + matrix2.get(row, col)));
              }
          }
          return matrixSum;
      }
      
      /**
       * Helper method to determine entries
       * 
       * @boolean if entries is 0
       */
      private boolean entriesIsZero()
      {
          //Checks if entries is 0
          if(getStoredEntries() == 0)
          {
              return true;
          }
          
          return false;
     }
      
      /**
       * Helper method gets specific column node
       * 
       * @param long desired column
       * @List column node
       */
      private List getSpecColumn(long columnIndex)
      {
          //Checks if list is empty
          if(entriesIsZero())
          {
              return null;
          }
          
          
          //Iterates through list until end of column list or finds desired column index
          List pos = listHolder; 
          List holder = null;
          
          while(pos != null )
          {
              //Found column index
              if(columnIndex == pos.colIdx)
              {
                  holder = pos;
                  break;
              }
              
              //Shifts column list
              pos = pos.getNextCol();
          }
          
          return holder;
      }
      
      /**
       * helper method to find Specific row
       * 
       * @param long the row index
       * @param List the column node list
       * @return List the desired row
       */
      private List getSpecRow(long rowIndex, List column)
      {
          //Checks if list is empty
          if(entriesIsZero() || column == null)
          {
              return null;
          }
          
          List pos = column; 
          List holder = null;
          
          //searches through column list
          while(pos != null)
          {
              //found the row
              if(rowIndex == pos.rowIdx)
              {
                  holder = pos;
                  break;
              }
              
              //shifts column row
              pos = pos.getNextRow();
          }
          
          return holder;
      }
      
      /**
       * Helper method to find if contains unique value
       * 
       * @param long row
       * @param long column
       * @boolean if the unique value exists in the list
       */
      public boolean contains(long row, long column)
      {
          //Checks if the speicific column exists
          if(getSpecColumn(column) != null)
          {
              //Checks if the specific row exists
              if(getSpecRow(row, getSpecColumn(column)) != null)
              {
                  return true;
              }
          }
          return false;
      }
      
      /**
       * Helper method to get previous column node
       * 
       * @List previous column
       */
      private List getPrevCol(long column)
      {
          //Stops one short of the desired column index
          List temp = listHolder;
          while(temp.getNextCol() != null && temp.getNextCol().colIdx < column)
          {
              temp = temp.getNextCol();
          }
          return temp;
      }
      
      /**
       * Helper method to get previous row node
       * 
       * @param long row desired row index
       * @param List column the current column node list
       * @return List previous row
       */
      private List getPrevRow(long row, List column)
      {
          //Stops one short of the desired row index
          List temp = column;
          while(temp.getNextRow() != null && temp.getNextRow().rowIdx < row )
          {
              temp = temp.getNextRow();
          }
          return temp;
      }
}
