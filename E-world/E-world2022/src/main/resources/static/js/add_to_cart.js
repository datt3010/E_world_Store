const app = angular.module("shopping-cart-app",[]);

app.controller("shopping-cart-ctrl",function($scope,$http){
$scope.cart={
    items:[],
    add(id){
    var item = this.items.find(item => item.id == id);
    if(item){
        item.qty++;
        this.saveToLocalStorage();
    }
    else{
        $http.get(`/rest/products/${id}`).then(resp =>{
            console.log(resp);
            resp.data.qty=1
            this.items.push(resp.data);
            this.saveToLocalStorage();
        })
    }
    },
    get count(){
    return this.items
    .map(item => item.qty)
    .reduce((total,qty) => total +=qty,0);
    },
    get amount(){
    return this.items
    .map(item => item.qty*item.price)
    .reduce((total,qty) => total+=qty,0);
    },
    saveToLocalStorage(){
    var json = JSON.stringify(angular.copy(this.items));
    localStorage.setItem("cart",json);
    },
    loadFromLocalStorage(){
    var json = localStorage.getItem("cart")
    this.items = json ? JSON.parse(json):[];
    },
    remove(id){
    var index = this.items.findIndex(item => item.id == id);
    this.items.splice(index,1);
    this.saveToLocalStorage();
    },
    clear(){
    this.items=[]
    this.saveToLocalStorage();
    }
}
$scope.cart.loadFromLocalStorage();
$scope.order ={
    createdAt: new Date(),
    address:"",
    phone: "",
    status:"IN_PROCRESS",
    paymentMethod: "CASH",
    totalPrice: $scope.cart.amount + $scope.cart.amount *0.1,
    account: {id:$("#accountId").text(),username:$("#username").text()},
    get orderDetails(){
       return $scope.cart.items.map(item =>{
           return{
           product:{id:item.id},
           productPrice:item.price,
           quantity:item.qty,
           }
       });
    },
    purchase(){
        var order = angular.copy(this);
        if($scope.order.address === "" || $scope.order.phone === ""){
               alert("vui lòng nhập số điện thoại và địa chỉ");
               return false;
           }
       if($scope.order.totalPrice < 1000){
                alert("Đơn hàng không hợp lệ");
                return false;
            }
        $http.post("/rest/orders",order).then(resp =>{
        alert("đặt hàng thành công");
        $scope.cart.clear();
       // location.href="/order/detail/" + rest.data.id;
       location.href="/product";
        }).catch(error =>{
            alert("Đặt hàng lỗi!")
            console.log(error)
        })
    }
}
})