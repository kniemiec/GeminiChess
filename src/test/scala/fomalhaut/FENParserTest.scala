package test.fomalhaut

import fomalhaut.helper.FENParser
import fomalhaut.{Board, Move}
import org.scalatest.FlatSpec


/**
 * @author kniemiec
 */
class FENParserTest extends FlatSpec{
   
  "An initial position " should " give such Board" in {
    val board: Board = new FENParser().parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

    printListofInt(board.fieldsOccupiedByWhite)
    printListofInt(board.fieldsOccupiedByBlack)
    
    
    printList(board.getAllWhiteMoves().flatten)
  }
  
  
  private def printListofInt(list: List[Int]){
    for( item <- list){
      println("("+item+")")
    }
  }    
  
  private def printList(list: List[Move]){
    for( item <- list){
      println("("+item.from+","+item.to+")")
    }
  }  
}