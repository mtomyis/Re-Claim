package com.conceptdesign.re_claim.Model

class Reimbursement {
    var id:Int=0
    var tgl:String?=null
    var reimburs:String?=null
    var status:Int=0
    var total:Int=0

    constructor(){}
    constructor(id:Int,tgl:String,reimburs:String,status:Int,total:Int)
    {
        this.id
        this.tgl
        this.reimburs
        this.status
        this.total
    }
}