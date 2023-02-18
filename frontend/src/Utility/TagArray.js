function TagArray(arrayOfIds){
    this.array = arrayOfIds;
}

TagArray.prototype.equals = function(comparedTagArray){
    if(!comparedTagArray.array) return false;
    if(comparedTagArray === this) return true;
    if(comparedTagArray.array.length !== this.array.length) return false;


    const copyArray = new TagArray([...comparedTagArray.array]);

    for(let i=0; i < this.array.length; i++){
        const index = copyArray.tagIndexOf(this.array[i]);
        if(index === -1) return false;
        copyArray.array.splice(index, 1)
    }
    return copyArray.array.length <= 0;

}
TagArray.prototype.toString = function(){
    return `[${this.array.join(', ')}]`
}
TagArray.prototype.tagIndexOf = function (tagIdToFind) {
    for (let i=0; i<this.array.length; i++) {
        if(tagIdToFind === this.array[i]) return i;
    }
    return -1;
}

module.exports = TagArray;