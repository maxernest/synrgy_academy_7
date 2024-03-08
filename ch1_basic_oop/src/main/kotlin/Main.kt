interface IShape{
    fun pyramid(space: Int = 0)
    fun reversePyramid(space: Int = 0)
    fun star()
    fun xSymbol()
    fun hollowPyramid()
}

class Shape(var n: Int):IShape{
    override fun pyramid(space: Int) {
        for (i in 1..n){
            for (j in i+1..n+space){
                print(" ")
            }
            for (k in 1..i-1){
                print("**")
            }
            print("*")
            println()
        }
    }

    override fun reversePyramid(space:Int) {
        for (i in 1..n){
            for (j in 1..i-1+space){
                print(" ")
            }
            for (j in i+1..n){
                print("**")
            }
            print("*")
            println()
        }
    }

    override fun star() {
        pyramid(1)
        for (i in 1..n){
            print("**")
        }
        print("*")
        println()
        reversePyramid(1)
    }

    override fun xSymbol() {
        for (i in 1..n){
            for (j in 1..i){
                print(" ")
            }
            print("*")
            for (j in i+1..n){
                print("  ")
            }
            print(" *")
            println()
        }
        for (i in 1..n+1){
            print(" ")
        }
        print("*")
        println()
        for (i in 1..n){
            for (j in i..n){
                print(" ")
            }
            print("*")
            for (k in 1..i-1){
                print("  ")
            }
            print(" *")
            println()
        }
    }

    override fun hollowPyramid() {
        for (i in 1..n){
            for (j in i+1..n+1){
                print("  ")
            }
            print("*")
            for (k in 1..i-1){
                print("  ")
            }
            for (k in 1..i-2){
                print("  ")
            }
            if (i > 1){
                print(" *")
            }
            println()
        }
        if (n > 1){
            for (i in 1..((n)*2)){
                print("* ")
            }
            print("*")
        }
    }

    fun setNumber(n: Int){
        this.n = n;
    }
}

fun main(args: Array<String>) {
    val shape = Shape(1)
    shape.xSymbol()
    shape.setNumber(2)
    shape.xSymbol()

}