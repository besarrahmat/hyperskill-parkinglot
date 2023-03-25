package parking

data class Car(val registration: String, val color: String)

class Lot(private var capacity: Int) {
    private var spots = Array<Car?>(capacity) { null }

    fun create(capacity: Int) {
        this.capacity = capacity
        spots = Array(capacity) { null }
        println("Created a parking lot with $capacity spots.")
    }

    fun park(registration: String, color: String) {
        if (capacity == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        var spot = -1

        for (i in spots.indices) {
            if (spots[i] == null) {
                spot = i
                break
            }
        }

        if (spot == -1) {
            println("Sorry, the parking lot is full.")
        } else {
            spots[spot] = Car(registration, color)
            println("$color car parked in spot ${spot + 1}.")
        }
    }

    fun leave(spot: Int) {
        if (capacity == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        if (spots[spot - 1] == null) {
            println("There is no car in spot $spot.")
        } else {
            spots[spot - 1] = null
            println("Spot $spot is free.")
        }
    }

    fun status() {
        if (capacity == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        var occupied = ""

        for (i in spots.indices) {
            if (spots[i] != null) {
                val car = spots[i]!!
                occupied += "${i + 1} ${car.registration} ${car.color}\n"
            }
        }

        if (occupied.isEmpty()) {
            println("Parking lot is empty.")
        } else {
            print(occupied)
        }
    }

    fun regByColor(color: String) {
        if (capacity == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        val cars = spots.filterNotNull().filter {
            it.color.equals(color, true)
        }

        if (cars.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            val reg = cars.map { it.registration }
            println(reg.joinToString())
        }
    }

    fun spotByColor(color: String) {
        if (capacity == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        val spots = spots.indices.filter {
            spots[it]?.color.equals(color, true)
        }.map { it + 1 }

        if (spots.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            println(spots.joinToString())
        }
    }

    fun spotByReg(reg: String) {
        if (capacity == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }

        val spot = spots.indexOfFirst { it?.registration == reg }

        if (spot == -1) {
            println("No cars with registration number $reg were found.")
        } else {
            println(spot + 1)
        }
    }
}

fun main() {
    val lot = Lot(0)

    while (true) {
        val input = readln().split(" ")

        when (input[0]) {
            "create" -> lot.create(input[1].toInt())
            "park" -> lot.park(input[1], input[2])
            "leave" -> lot.leave(input[1].toInt())
            "status" -> lot.status()
            "reg_by_color" -> lot.regByColor(input[1])
            "spot_by_color" -> lot.spotByColor(input[1])
            "spot_by_reg" -> lot.spotByReg(input[1])
            "exit" -> break
        }
    }
}