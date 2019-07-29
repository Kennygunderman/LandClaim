package com.kgeezy.landclaim.claim

data class Center(val x: Int, val z: Int)

class Claim(val center: Center, val radius: Int) {

    fun isInClaim(x: Int, z: Int): Boolean {
        val x1z1 = x1z1()
        val x2z2 = x2z2()
        return x >= x1z1.first && z >= x1z1.second && x <= x2z2.first && z <= x2z2.second
    }

    /**
     * Compares 4 corners of the passed in claim to the current claim to check
     * if there is any overlapping.
     *
     * @param claim
     */
    fun isInClaim(claim: Claim): Boolean {
        val x1 = x1z1().first
        val x2 = x2z2().first
        val z1 = x1z1().second
        val z2 = x2z2().second

        val _x1 = claim.x1z1().first
        val _x2 = claim.x2z2().first
        val _z1 = claim.x1z1().second
        val _z2 = claim.x2z2().second


        print("$x1 $x2 $z1 $z2 $_x1 $_x2 $_z1 $_z2")

        return ((_x2 in x1..x2 && _z2 in z1..z2) // Top right is touching
                || (_x2 in x1..x2 && _z1 in z1..z2) //Bottom right is touching
                || (_x1 in x1..x2 && _z2 in z1..z2) //Top left is touching
                || (_x1 in x1..x2 && _z1 in z1..z2)) //Bottom left is touching
    }

    /**
     * Bottom left corner of square
     */
    private fun x1z1(): Pair<Int, Int> {
        val x1 = center.x - radius
        val z1 = center.z - radius
        return Pair(x1, z1)
    }

    /**
     * Top right corner of square
     */
    private fun x2z2(): Pair<Int, Int> {
        val x2 = center.x + radius
        val z2 = center.z + radius
        return Pair(x2, z2)
    }
}