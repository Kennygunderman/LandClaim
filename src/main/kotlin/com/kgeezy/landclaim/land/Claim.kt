package com.kgeezy.landclaim.land

data class Center(val x: Int, val z: Int)

class Claim(private val center: Center, private val radius: Int) {

    fun isInClaim(x: Int, z: Int): Boolean {
        val x1z1 = x1z1()
        val x2z2 = x2z2()
        return x >= x1z1.first && z >= x1z1.second && x <= x2z2.first &&  z <= x2z2.second
    }

    private fun x1z1(): Pair<Int, Int> {
        val x1 = center.x - radius
        val z1 = center.z - radius
        return Pair(x1, z1)
    }

    private fun x2z2(): Pair<Int, Int> {
        val x2 = center.x + radius
        val z2 = center.z + radius
        return Pair(x2, z2)
    }
}