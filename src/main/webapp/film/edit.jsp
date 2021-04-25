<!doctype html>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Modifica Elemento</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="./assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../navbar.jsp" />
	
	<main role="main" class="container">
	
		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
		  ${errorMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Inserisci nuovo elemento</h5> 
		    </div>
		    <div class='card-body'>

					<form method="post" action="ExecuteUpdateFilmServlet" novalidate="novalidate" >
					
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Titolo</label>
								<input type="text" name="titolo" id="titolo" class="form-control" placeholder="Inserire il titolo" value="${film_da_modificare.titolo }">
							</div>
							
							<div class="form-group col-md-6">
								<label>Genere</label>
								<input type="text" name="genere" id="genere" class="form-control" placeholder="Inserire il genere" value="${film_da_modificare.genere }">
							</div>
						</div>
						
						<div class="form-row">	
							<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${film_da_modificare.dataPubblicazione}' />
							<div class="form-group col-md-6">
								<label>Data di Pubblicazione</label>
                        		<input class="form-control" id="dataPubblicazione" type="date" placeholder="dd/MM/yy"
                            		title="formato : gg/mm/aaaa"  name="dataPubblicazione" value="${parsedDate}" >
							</div>
							<div class="form-group col-md-6">
								<label>Durata (minuti)</label>
								<input type="number" class="form-control" name="minutiDurata" id="minutiDurata" placeholder="Inserire la durata" value="${film_da_modificare.minutiDurata }">
							</div>
						</div>
						<div class="form-row">	
							<div class="form-group col-md-6">
								<label for="regista.id">Regista</label>
							    <select class="form-control" id="regista.id" name="regista.id">
							    	<option value="" selected> -- Selezionare una voce -- </option>
							      	<c:forEach items="${registi_list_attribute }" var="registaItem">
							      		<option value="${registaItem.id}" ${film_da_modificare.regista.id == registaItem.id?'selected':''} >${registaItem.nome } ${registaItem.cognome }</option>
							      	</c:forEach>
							    </select>
							</div>
						</div>
						
						<input type="hidden"   name="idFilm"   value="${film_da_modificare.id }">	
						<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
						
					</form>

		    
		    
			<!-- end card-body -->					   
		    </div>
		</div>	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>