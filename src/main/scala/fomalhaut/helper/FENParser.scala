package fomalhaut.helper

import fomalhaut.pieces.PieceType
import fomalhaut.pieces._
import PieceType._
import fomalhaut.{Board, BoardSpecialEvents}


class FENParser {
  
  val whiteSymbols = "KQRBNP"
  val blackSymbols = "kqrbnp"

    var whitePieces: List[Piece] = Nil
    var blackPieces: List[Piece] = Nil
  var globalContext: BoardSpecialEvents = new BoardSpecialEvents(0,true,true,true,true,0)

    val REDUCE_PATTERN = List( PieceType.QEEN,PieceType.KING,PieceType.ROOK,PieceType.BISHOP,PieceType.KNIGHT,PieceType.PAWN)
    var boardRow  = 7
    var boardCol = 0

  
  def parseFENDescriptiontToBoard(fen: String): Board = {
    var piecesPosition: Array[String] = fen.split(" ")
    val boardContext: BoardSpecialEvents = calculateContext(piecesPosition(1),piecesPosition(2),piecesPosition(3)) 
    globalContext = boardContext;

    initializeEmptyBoard(boardContext)
    
    val figures = piecesPosition(0)
    figures.map( c => updateBoard(c, boardContext))
//    whitePieces =
    
    val whiteOnMove = calculateWhiteOnMove(piecesPosition(1))
    new Board(REDUCE_PATTERN.map(filterPiecesByType(_,whitePieces)),
              REDUCE_PATTERN.map(filterPiecesByType(_,blackPieces)),
              boardContext)
  }

  def reducePiecesByType(p1: Piece, p2: Piece) : Piece = p1 match {
    case Queen(positions,context) => new Queen(p1.getPositions() ::: p2.getPositions(),globalContext)
    case King(positions,context) => new King(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Rook(positions,context) => new Rook(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Bishop(positions,context) => new Bishop(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Knight(positions,context) => new Knight(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Pawn(positions,context) => new Pawn(p1.getPositions() ::: p2.getPositions(),globalContext)
  }


  def filterPiecesByType(pieceType: PieceType, piecesList: List[Piece]) : Piece = {
    piecesList.filter( _.getPieceType() == pieceType).reduce(reducePiecesByType(_,_))
  }

  
  def initializeEmptyBoard(context: BoardSpecialEvents) = {
    whitePieces = List( Queen(List(),context),King(List(),context),Rook(List(),context),Bishop(List(),context),Knight(List(),context),Pawn(List(),context))
    blackPieces = List( Queen(List(),context),King(List(),context),Rook(List(),context),Bishop(List(),context),Knight(List(),context),Pawn(List(),context))
  }
  
  def calculateContext(onMove: String,castlingS: String, enpassant: String): BoardSpecialEvents = {
    val whiteOnMove:Int = calculateWhiteOnMove(onMove);
    val isShortCastlingAllowedForWhite = castlingS.indexOf("K") > -1
    val isLongCastlingAllowedForWhite = castlingS.indexOf("Q") > -1
    val isLongCastlingAllowedForBlack = castlingS.indexOf("q") > -1
    val isShortCastlingAllowedFoBlack = castlingS.indexOf("k") > -1
    var enPassantMove = 0
    if(enpassant == "-"){
      enPassantMove = 0;
    } else { 
      enPassantMove = Integer.parseInt(enpassant)
    }
    new BoardSpecialEvents(whiteOnMove,isShortCastlingAllowedForWhite,isLongCastlingAllowedForWhite,
        isShortCastlingAllowedFoBlack,isLongCastlingAllowedForBlack,enPassantMove)
  }
  
  def calculateWhiteOnMove(c: String): Int = {
    if(c == "w") 0
    else 1
  }
  
  def updateBoard(c: Char, context: BoardSpecialEvents) = {
      //println(c)
      if(whiteSymbols.contains(c)){
        whitePieces = whitePieces ::: List(createPieceFromIndex(whiteSymbols.indexOf(c),boardRow*8+boardCol,context))
        boardCol += 1
      } else if(blackSymbols.contains(c)){
        blackPieces = blackPieces ::: List(createPieceFromIndex(blackSymbols.indexOf(c),boardRow*8+boardCol,context))
        boardCol += 1
      } else if(c == '/'){
        boardCol = 0;
        boardRow -= 1
      } else {
        boardCol += c.toInt-'1'.toInt+1
      }
      ""
  }

  def saveBoardtoFENDescription(board: Board): String = {
    ""
  }
  
  def createPieceFromIndex(pieceIndex: Int, boardIndex: Int, context: BoardSpecialEvents): Piece = {
    pieceIndex match {
      case 0 => new King(List(boardIndex),context)
      case 1 => new Queen(List(boardIndex),context)
      case 2 => new Rook(List(boardIndex),context)
      case 3 => new Bishop(List(boardIndex),context)
      case 4 => new Knight(List(boardIndex),context)
      case 5 => new Pawn(List(boardIndex),context)
    }
  }
  
   

}