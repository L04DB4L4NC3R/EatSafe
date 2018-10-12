const express = require("express");
const bp = require("body-parser");
const mongoose = require("mongoose");
require("dotenv").config();
const app = express();

require("morgan")("dev");
app.use(bp.json());
app.use(bp.urlencoded({extended:false}));

mongoose.connect(process.env.DBURL,{useNewUrlParser:true});
mongoose.connection.once("open",()=>console.log("Connected to DB"))
.on("error",()=>console.log("error connecting to DB"));

app.get("/",(req,res)=>{
    res.send("Hello world");
});


app.use("/bc",require("./routes/transaction"));


server = app.listen(process.env.PORT || 3000,()=>console.log(`Listening on port ${server.address().port}`))