const crypto = require("crypto");


class Block{
    constructor(index,previousHash,timestamp,hash,data){
        this.index = index;
        this.timestamp = timestamp;
        this.hash = hash;
        this.previousHash = previousHash;
        this.data = data;
    }
}




var calcHash = (index,previousHash,data,timestamp)=>{
    return crypto.createHmac('sha256',"10")
    .update(index.toString()+previousHash+timestamp+data)
    .digest('hex');
}



exports.genesisInit = (data)=>{

    return new Block(0,"GENESIS PREVIOUS HASH",Date.now()/1000,calcHash(0,"PH","GD",Date.now()/1000),data)
}




exports.createBlock = (data,timestamp,latestBlock)=>{

    let thisIndex = latestBlock.index+1;

    let thisHash = calcHash(thisIndex,latestBlock.hash,data,timestamp)  ;

    return new Block(thisIndex,latestBlock.hash,timestamp,thisHash,data);

}

exports.validateChain = (newBlock,oldBlock)=>{
    if(newBlock.index!=oldBlock.index+1)
        return '1';
    if(newBlock.previousHash!=oldBlock.hash)
        return '2';
    if(calcHash(newBlock.index,newBlock.previousHash,newBlock.data,newBlock.timestamp)!=newBlock.hash)
        return '3';
    return true;
}