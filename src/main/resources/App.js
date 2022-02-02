import React,{useState, useEffect} from 'react'; 

    
const App = () => {  
 
// const [toDoItems, setToDoItems] = useState(null)
// // const [toDoItems, setToDoItems] = React.useState([])
// const [item, setItem] = useState(" ")
// useEffect(() => {

//   // do something on load
//   console.log("I have loaded up")

//   if (!toDoItems) {
//     console.log("dukse")
//     console.log("duks22e")
//     console.log("dukse222222")
//     fetch("http://192.168.0.104:9999/api/v1/report/list/")
//       .then((response) => response.json())
//       .then((data) => {
//         console.log("To Do Item List ", data)

//         console.log("testing", toDoItems);

//         setToDoItems(data);

      
//         console.log("test ", toDoItems);

//       })
//       .catch((err) => {
  
//       console.log("errorrrr")
//         });
//     }
//     },[]);

const [data, setData] = useState([]);

useEffect(() => {
  const fetchData = async () => {


    console.log('ok', data)

    if(data.length===0){
      console.log("test", data)
      const res = await fetch(
        'http://192.168.0.104:9999/api/v1/report/list/',
      );

      // console.log("try", res);
      const json = await res.json();
      setData(json);
      console.log("test" ,data)
    }
    
  };
  fetchData();
});

  return (  
    <div className="container">  
        <h1> Example of React Map Loop </h1>  
        

     
        <table className="table table-bordered " style={{border: "1px solid black"}} >  
            <tr>  
                <th>ID</th>  
                <th>Name</th>  
                <th>Action</th>  
            </tr>  

            {
            
            data.map((student, index) => (  
              <tr data-index={index} >  
                <td>{student.id}</td>  
                <td>{student.name}</td>
                <td><a href={"http://192.168.0.104:9999/api/v1/report/generate-report/" + student.id}>download</a></td>  
              </tr>  
            ))}  
    
        </table>  
    
    </div>  
  );  
}  


 
 
    

export default App;  
