package com.conceptdesign.re_claim.Model

class Reimbursement {
    var id:Int=0
    var tgl:String?=null
    var reimburs:String?=null
    var status:Int=0
    var total:String?=null

    constructor(){}
    constructor(id:Int,tgl:String,reimburs:String,status:Int,total:String)
    {
        this.id
        this.tgl
        this.reimburs
        this.status
        this.total
    }
}