package fomalhaut.eva

import fomalhaut.helper.MoveLogger
import fomalhaut._
import fomalhaut.pieces.BoardTestHelper

object MoveEvaluator {
  
  val MAX_DEPTH : Int = 3

  var bestPathMoves : Array[Move] = new Array[Move](MAX_DEPTH)

  var currentlyAnalyzedPathMoves = Array.ofDim[Move](MAX_DEPTH,MAX_DEPTH)

  var bestPathMovesMarker : Int = 0
  
  def generateBoardsList(board: Board): List[Board] = {
    board.generateAllReachableBoards()    
  }


  def findBestMove(board: Board): Move = {
    if(board.getBoardSpecialEvents().getColorToMove() == 0){
      findBestWhiteMove(board)
    } else {
      findBestBlackMove(board)
    }
  }



  def findBestWhiteMove(board: Board): Move = {
    val movesList : List[Move] = board.getAllMoves()
    var maxValues = - 2000000
    var moveFound = movesList(1)
    for(move <- movesList){
      MoveLogger.logMove(move)
      val evaluated = evaluateMoveForWhite(board, move, MAX_DEPTH-1)
      if(evaluated > maxValues){
        maxValues = evaluated
        moveFound = move
        pushMove(move,MAX_DEPTH)
//        MoveLogger.logMove(move,maxValues)
      }
    }
    println("finished")
    MoveLogger.printPathEvaluation(bestPathMoves, maxValues)
    moveFound
  }

  def findBestBlackMove(board: Board): Move = {
    val movesList : List[Move] = board.getAllMoves()
    var maxValues = 2000000
    var moveFound = movesList(1)
    for(move <- movesList){
      MoveLogger.logMove(move)
      val evaluated = evaluateMoveForBlack(board, move, MAX_DEPTH-1)
      if(evaluated < maxValues){
        maxValues = evaluated
        moveFound = move
        pushMove(move,MAX_DEPTH)
        //        MoveLogger.logMove(move,maxValues)
      }
    }
    println("finished")
    MoveLogger.printPathEvaluation(bestPathMoves, maxValues)
    moveFound
  }

  def evaluateMoveForBlack(board: Board, move: Move, depth : Int): Int = {
    //    initalize()
    // skad wziac attackedFields
    val newBoard : Board = board.generateBoardAfterMove(move)
    var moveList : Array[Move] = new Array[Move](10)
    moveList(0) = move
    return findMaxValue(newBoard,depth,moveList)
  }

  def evaluateMoveForWhite(board: Board, move: Move, depth : Int): Int = {
    val newBoard : Board = board.generateBoardAfterMove(move)
    var moveList : Array[Move] = new Array[Move](10)
    moveList(0) = move
    return findMinValue(newBoard,depth,moveList)
  }

  def findMaxValue(board: Board, depth: Int, moveList: Array[Move]) : Int = {
      if(depth == 0){
        val value : Integer = BoardValueCalculator.calculateBoardValue(board)
//        print(value+" ")
        value
      } else if (BoardValueCalculator.calculateKingsBalance(board) != 0) {
        BoardValueCalculator.calculateKingsBalance(board)
      } else {
          var maxValue = -200000
          val movesList : List[Move] = board.getAllMoves()
          //        val evaluationValues: List[Int] =   boardsList.map(evaluateBoard(_,depth+1),maxDepth)
          for(move <- movesList){
            //          MoveLogger.logMove(move)
            val newBoard : Board = board.generateBoardAfterMove(move)
            val score = findMinValue(newBoard,depth-1,moveList)
            //          MoveLogger.logMove(move,score)
            if(score > maxValue){
              pushMove(move,depth)
//              moveList(MAX_DEPTH-depth+1) = move
              maxValue = score
//              if(depth == MAX_DEPTH -1) {
//                 MoveLogger.printPathEvaluation(currentlyAnalyzedPathMoves(3), maxValue)
//              }
            }
          }
          maxValue
      }
  }

  def findMinValue(board: Board, depth: Int, moveList: Array[Move]) : Int = {
    if(depth == 0){
      val value : Integer = BoardValueCalculator.calculateBoardValue(board)
//      print(value+" ")
      value
    } else if (BoardValueCalculator.calculateKingsBalance(board) != 0) {
      BoardValueCalculator.calculateKingsBalance(board)
    } else {
        var minValue : Int = 200000
        val movesList : List[Move] = board.getAllMoves()
//        val attackedFields = movesList.groupBy(_.to).map(_._2.head).map(_.to).toList
        //        val evaluationValues: List[Int] =   boardsList.map(evaluateBoard(_,depth+1),maxDepth)
        for(move <- movesList){
          //          MoveLogger.logMove(move)
          val newBoard : Board = board.generateBoardAfterMove(move)

          val score : Int = findMaxValue(newBoard,depth-1,moveList)
          //          MoveLogger.logMove(move,score)
          if(score < minValue){
            pushMove(move,depth)
            minValue = score
//            if(depth == MAX_DEPTH -1) {
//              MoveLogger.printPathEvaluation(currentlyAnalyzedPathMoves(3), minValue)
//            }
          }
        }
        //        println("depth end -> "+ depth+ " max value ->"+maxValue)
        minValue
    }
  }

  def initalize() = {
    for(i: Int <- 0 until MAX_DEPTH-1){
      currentlyAnalyzedPathMoves(i) = new Array[Move](MAX_DEPTH)
    }

  }


  def pushMove(move: Move, depth: Int) = {
    //    if(depth == 1 ) {
    //      currentlyAnalyzedPathMoves(0)(MAX_DEPTH - depth) = move
    //    } else if(depth == 2){
    //      currentlyAnalyzedPathMoves(0)(MAX_DEPTH - depth) = move
    //      rewritePath(0,1,1)
    //    } else if(depth == 3){
    //      println(depth)
    //      currentlyAnalyzedPathMoves(1)(MAX_DEPTH - depth) = move
    //      MoveLogger.printPathEvaluation(currentlyAnalyzedPathMoves(1),0)
    //      rewritePath(1,2,2)
    //    } else
    if (depth == MAX_DEPTH) {
      currentlyAnalyzedPathMoves(MAX_DEPTH-2)(MAX_DEPTH - depth) = move
//      MoveLogger.printPathEvaluation(currentlyAnalyzedPathMoves(2), 0)
      rewritePath(MAX_DEPTH-2, MAX_DEPTH-1, MAX_DEPTH-1)
      copyCurrentToBest()
    } else {
      pushInternal(move,depth,MAX_DEPTH-1)

    }
  }

    def pushInternal(move: Move, depth: Int, marker: Int) : Int = {
      if(marker == 1){
        currentlyAnalyzedPathMoves(marker-1)(MAX_DEPTH-depth) = move
      } else if(depth == marker){
        currentlyAnalyzedPathMoves(marker-2)(MAX_DEPTH-depth) = move
        rewritePath(marker-2, marker-1, marker-1)
      } else {
        pushInternal(move, depth, marker-1)
      }
      return 0
    }

//    if(depth >= bestPathMovesMarker){
//      copyCurrentToBest(depth)
//      bestPathMovesMarker = depth
//    }
//  }

  def rewritePath(fromDepth: Int, toDepth: Int, amount : Int)= {
    for(i: Int <- 0 to amount){
      currentlyAnalyzedPathMoves(toDepth)(MAX_DEPTH-i-1) =
        currentlyAnalyzedPathMoves(fromDepth)(MAX_DEPTH-i-1)
    }
  }

  def copyCurrentToBest() = {
    for(i: Int <- 0 until MAX_DEPTH){
      bestPathMoves(i) = currentlyAnalyzedPathMoves(MAX_DEPTH-1)(i)
    }

  }



}
