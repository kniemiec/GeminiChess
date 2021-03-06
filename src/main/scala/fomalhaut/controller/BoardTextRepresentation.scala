package fomalhaut.controller

import fomalhaut.Board
import fomalhaut.pieces.Piece

/**
 * Created by kniemiec on 18.02.16.
 */
class BoardTextRepresentation(board: Board) {

  val BOARD_SIZE : Int = 8

  var fieldsArray = decorateBoard(getInitialArray(),board)

  def getInitialArray(): Array[Array[Int]] = {
    val tmpArray = Array.ofDim[Int](BOARD_SIZE,BOARD_SIZE)
    for(i: Int <- 0 until BOARD_SIZE ){
      for(j: Int <- 0 until BOARD_SIZE){
        tmpArray(i)(j) = getFieldColor(i,j)
      }
    }
    tmpArray
  }

  def decorateBoard(input: Array[Array[Int]], board: Board): Array[Array[Int]] = {
    var tmpArray = Array.ofDim[Int](BOARD_SIZE,BOARD_SIZE)
    tmpArray = input
    board.getWhitePiecesPosition()
      .map( (piece) => {
        piece.getPositions().map( (position) => {
          val row : Int = position / 8
          val col : Int = position % 8
          tmpArray(row)(col) = piece.getPieceCode()
        })
      })
    board.getBlackPiecesPosition()
      .map( (piece) => {
        piece.getPositions().map( (position) => {
          val row : Int = position / 8
          val col : Int = position % 8
          tmpArray(row)(col) = piece.getPieceCode()+32
        })
      })

    tmpArray
  }

  private def getFieldColor(r:Int, c:Int): Int = {
    if((r+c) % 2 == 0) { 31 }
    else { 132 }
  }

  def printBoard() = {
    for(i: Int <- 0 until BOARD_SIZE ) {
      for (j: Int <- 0 until BOARD_SIZE) {
        print(fieldsArray(i)(j).toChar)
      }
      println("")
    }
  }
}
