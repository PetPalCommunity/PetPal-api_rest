# PetPal-api_rest

## Descripción:

*Como usuario, quiero poder solicitar servicios de cuidado de mascotas, especificando fechas, horarios y requisitos específicos para contratar sus servicios.*

**Escenario 1**: Usuario solicita un veterinario  a través de la app.

**Dado** que el usuario se encuentra en el perfil de un veterinario.

**Cuando** el usuario dé clic en “Solicitar servicios”.

**Entonces** la aplicación mostrará una ventana emergente donde el usuario deberá completar datos como la fecha, hora y los requisitos específicos necesarios para proceder con el servicio. Finalmente, el usuario deberá dar clic en “Enviar solicitud” para proceder con esta.

**Escenario 2**: Veterinario acepta una solicitud de servicios de cuidado.

**Dado** que el veterinario se encuentra en el centro de notificaciones de su perfil.

**Cuando** el veterinario dé clic a notificación de solicitud de servicios de cuidado.

**Entonces** la aplicación mostrará una ventana emergente donde podrá ver los detalles de la solicitud enviada y deberá dar clic en “Aceptar” para agendar este servicio de cuidado de mascotas.
