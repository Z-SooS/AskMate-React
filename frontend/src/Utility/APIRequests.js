const APIRequests = {
    get: async(route)=> {
        return await fetch(`${process.env.BACKEND_ADDRESS}${route}`)
    },
    post: async(route, bodyObject)=> {
        return await fetch(`${process.env.BACKEND_ADDRESS}${route}`, {
            method:"POST",
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify(bodyObject)
        })
    }
}

export default APIRequests