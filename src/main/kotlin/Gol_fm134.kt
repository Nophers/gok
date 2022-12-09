const val WIDTH = 100
const val HEIGHT = 100

fun main() {
    var cells = Array(WIDTH) { BooleanArray(HEIGHT) }

    for (i in 0 until WIDTH) {
        for (j in 0 until HEIGHT) {
            cells[i][j] = Math.random() < 0.5
        }
    }

    for (step in 0 until 100) {
        for (i in 0 until WIDTH) {
            for (j in 0 until HEIGHT) {
                print(if (cells[i][j]) "#" else ".")
            }
            println()
        }
        println()

        val nextCells = Array(WIDTH) { BooleanArray(HEIGHT) }
        for (i in 0 until WIDTH) {
            for (j in 0 until HEIGHT) {
                var neighbors = 0
                for (ii in i - 1..i + 1) {
                    for (jj in j - 1..j + 1) {
                        if (ii in 0 until WIDTH && jj >= 0 && jj < HEIGHT && (ii != i || jj != j)) {
                            if (cells[ii][jj]) {
                                neighbors++
                            }
                        }
                    }
                }

                nextCells[i][j] = when {
                    cells[i][j] && neighbors in 2..3 -> true
                    !cells[i][j] && neighbors == 3 -> true
                    else -> false
                }
            }
        }
        cells = nextCells
    }
}
