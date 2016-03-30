package test.fomalhaut.evaluation

import fomalhaut.Board
import fomalhaut.{BoardSpecialEvents, Move}
import fomalhaut.eva.MoveEvaluator
import fomalhaut.helper.{FENParser, MoveLogger}
import fomalhaut.pieces.{BoardTestHelper, Piece, PieceType}
import PieceType._
import org.scalatest.{FlatSpec, Ignore}

class MoveEvaluatorTest extends FlatSpec{

//  "move 1.e4 " should " be evaluated to 10 " in {
//     val initialPosition: Board = new FENParser().parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
//
//     assert(MoveEvaluator.evaluateMove(initialPosition, new Move(12,28,PieceType.PAWN,PieceType.PAWN),MoveEvaluator.MAX_DEPTH) == 10)
//  }

  "initial position " should " give us move " in {
    val initialPosition: Board = new FENParser().parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")

    val move: Move = MoveEvaluator.findBestMove(initialPosition)
    MoveLogger.logMove(move)
  }

  "position " should " give move Wg8g2 " in {
    val initialPosition: Board = new FENParser().parseFENDescriptiontToBoard("6W1/8/8/8/2K5/7W/1k6/8 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    MoveLogger.logMove(bestMove)
  }


  "positio n " should " give move Wg8g2 " in {
    val initialPosition: Board = new FENParser().parseFENDescriptiontToBoard("6R1/8/8/8/2K5/7R/1k6/8 w - 43 0 1")


    val list : List[Int]= initialPosition.generateFieldsAttackedByBlack()
    BoardTestHelper.printListofInt(list)
//    MoveLogger.logMove(bestMove)
  }

//  "mating move " should " be evaluated to 100 000 " in {
//    val initialPosition: Board = new FENParser().parseFENDescriptiontToBoard("4k3/RQ6/8/8/8/8/8/4K3 w KQkq 43 0 1")
//    assert(MoveEvaluator.evaluateBoard(initialPosition, 3) > 10000)
//
//  }
//
//  "initial  position " should " be evaluated to 0" in {
//    val initialPosition: Board = new FENParser().parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
//
//    assert(MoveEvaluator.evaluateBoard(initialPosition, 4) == 0)
//
//  }

}  
