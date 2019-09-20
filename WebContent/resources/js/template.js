function verificar(xhr, status, args, dlg, tbl) {
	if (args.validationFailed) {
		PF(dlg).jq.effect("shake", {
			times : 3
		}, 100);
	} else {
		PF(dlg).hide();
		PF(tbl).clearFilters();
	}
}

/*function handleLoginRequest(xhr, status, args) {
if (args.validationFailed || !args.loggedIn) {
	PF('dlg').jq.effect("shake", {
		times : 5
	}, 100);
} else {
	PF('dlg').hide();
	$('#loginLink').fadeOut();
}
}*/
