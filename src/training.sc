object training {
	
	 var lista = List(1,2,5,4,3)              //> lista  : List[Int] = List(1, 2, 5, 4, 3)
	 def compareElements(l: Int, r: Int): Boolean  = {
	 		l <= r
	 }                                        //> compareElements: (l: Int, r: Int)Boolean
	 
	lista.sortWith{(x,y)  =>  x > y }         //> res0: List[Int] = List(5, 4, 3, 2, 1)
	 
	lista.sortWith(compareElements)           //> res1: List[Int] = List(1, 2, 3, 4, 5)

	List( "Tom", "John", "Bob").sortWith(_.compareTo(_) < 0)
                                                  //> res2: List[String] = List(Bob, John, Tom)
}