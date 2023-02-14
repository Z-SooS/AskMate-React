const breadCrumbFunctions = {
    set: null,
    add: null,
    setUpBreadCrumb:(addFunction, setFunction) => {
        breadCrumbFunctions.set = setFunction;
        breadCrumbFunctions.add = addFunction;
    }
}

export default breadCrumbFunctions;