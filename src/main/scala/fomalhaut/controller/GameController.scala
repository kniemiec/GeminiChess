package fomalhaut.controller

import fomalhaut.eva.MoveEvaluator
import fomalhaut.helper.FENParser
import fomalhaut.{ Move, Board}

/**
 * Created by kniemiec on 11.02.16.
 */
class GameController {

  def getBoardAfterUserMove(currentBoard: Board, userMove: Move): Board = {
    //@TODO - not resolved attackedFields list problem
    return currentBoard.generateBoardAfterMove(userMove)
  }


  def getInitialBoard(): Board = {
    new FENParser().parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
  }

  def isGameFinished(board: Board): Boolean = {
    false
  }

  def getNewBoard(board: Board): Board = {
    board.generateBoardAfterMove(MoveEvaluator.findBestMove(board))
  }



}
