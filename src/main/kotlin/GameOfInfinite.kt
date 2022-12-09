class GameOfLife(private val rows: Int, private val cols: Int) {
    private val grid = Array(rows) { BooleanArray(cols) }
    val next = Array(rows) { BooleanArray(cols) }

    private fun initialize() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                grid[row][col] = (Math.random() < 0.5)
            }
        }
    }

    private fun update() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val neighbors = countNeighbors(row, col)
                next[row][col] = when {
                    grid[row][col] && neighbors in 2..3 -> true
                    !grid[row][col] && neighbors == 3 -> true
                    else -> false
                }
            }
        }

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                grid[row][col] = next[row][col]
            }
        }
    }

    private fun countNeighbors(row: Int, col: Int): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                count += if (get(row + i, col + j)) 1 else 0
            }
        }
        return count
    }

    fun get(row: Int, col: Int): Boolean {
        val newRow = (row + rows) % rows
        val newCol = (col + cols) % cols
        return grid[newRow][newCol]
    }

    fun runSimulation() {
        val game = GameOfLife(40, 200)
        game.initialize()

        while (true) {
            game.update()
            print("\u001b[2J\u001b[H")
            for (row in 0 until game.rows) {
                for (col in 0 until game.cols) {
                    print(if (game.get(row, col)) "\u2588" else " ")
                }
                println()
            }
            Thread.sleep(100)
        }
    }
}

fun main() {
    GameOfLife(0, 0).runSimulation()
}
