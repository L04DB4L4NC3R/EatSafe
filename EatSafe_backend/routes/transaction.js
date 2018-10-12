const router=require('express').Router();

const {
    genesisInit,
    createBlock,
    validateChain
} = require("../helpers/bc");

const chain = require("../schema/schema").chain;
const tester = require("../helpers/ml");


/**
 * params:  from:String,
            to:String,
            asset:String,
            quants:Number,
            quality:{
                temperature,humidity,gas,airqual,soilmois
            }
 */
router.post("/transact",async (req,res,next)=>{

    let quality = tester(req.body.quality);
    console.log(quality);
    if(quality.error>1)
        return res.json({message:"Food quality not upto the mark"});
    let data = await chain.find({});
    if(data.length < 1){

        let arr = genesisInit(req.body);
        res.json(await chain.create(arr));
        
    } else{
        let lastBlock = data[data.length-1];
        let arr = createBlock(req.body,Date.now()/1000,lastBlock);
        if(!validateChain(arr,lastBlock))
            return res.json({message:"Error validating blockchain"});
        res.json(await chain.create(arr));
    }
    
 });


 router.get("/blocks",async (req,res,next)=>{
    res.json(await chain.find({}));
 });






module.exports = router;