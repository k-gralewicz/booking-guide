function selectShopOrService() {
    //    alert("Wybrano sklep JS");
    var isVisit = $('#isVisit').val();
    var shopId = $('#shop').val();
    var serviceId = $('#service').val();

    const params = new URLSearchParams({ username: 'Kamil', selectedShopId: shopId, selectedServiceId: serviceId });
    var url = '';
    if (isVisit) {
        url = '/visits?' + params;
    } else {
        url = '/clients/dashboard?' + params;
    }
    fetch(url)
        // .then(response => response.json())
        .then(data => {
            // handle the response data
            console.log(url);
            console.log(isVisit)
            console.log(shopId);
            console.log(serviceId);
            // reload the page with the fetch URL
            window.location.href = url;

        })
        .catch(error => {
            // handle errors
            console.error('error:', error);
        });
}