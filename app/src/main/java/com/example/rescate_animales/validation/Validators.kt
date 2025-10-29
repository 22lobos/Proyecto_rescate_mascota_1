package com.example.rescate_animales.validation

import android.util.Patterns

/** Error de un campo específico */
data class FieldError(
    val field: String,
    val message: String
)

/** Resultado general de una validación */
data class ValidationResult(
    val ok: Boolean,
    val errors: List<FieldError> = emptyList()
) {
    val firstMessageOrNull: String? get() = errors.firstOrNull()?.message
}

/* ─────────────── Validadores básicos ─────────────── */

fun validateNonEmpty(field: String, value: String?, label: String): FieldError? {
    val v = value?.trim().orEmpty()
    return if (v.isEmpty()) FieldError(field, "El $label es obligatorio") else null
}

fun validateEmail(field: String, value: String?): FieldError? {
    val v = value?.trim().orEmpty()
    if (v.isEmpty()) return FieldError(field, "El correo es obligatorio")
    return if (!Patterns.EMAIL_ADDRESS.matcher(v).matches())
        FieldError(field, "El correo no tiene un formato válido")
    else null
}

/**
 * Reglas de contraseña:
 * - ≥ 8 caracteres
 * - Al menos 1 mayúscula, 1 minúscula y 1 número
 */
fun validatePassword(field: String, value: String?): FieldError? {
    val v = value.orEmpty()
    if (v.isEmpty()) return FieldError(field, "La contraseña es obligatoria")
    if (v.length < 8) return FieldError(field, "Debe tener al menos 8 caracteres")
    if (!v.any(Char::isUpperCase)) return FieldError(field, "Debe incluir una letra mayúscula")
    if (!v.any(Char::isLowerCase)) return FieldError(field, "Debe incluir una letra minúscula")
    if (!v.any(Char::isDigit))     return FieldError(field, "Debe incluir un número")
    return null
}

fun validateConfirmPassword(field: String, pass: String?, confirm: String?): FieldError? {
    if (confirm.isNullOrEmpty()) return FieldError(field, "Debes confirmar la contraseña")
    return if (pass != confirm) FieldError(field, "Las contraseñas no coinciden") else null
}

fun validateName(field: String, value: String?, label: String = "nombre"): FieldError? {
    val v = value?.trim().orEmpty()
    if (v.isEmpty()) return FieldError(field, "El $label es obligatorio")
    if (v.length < 2) return FieldError(field, "El $label debe tener al menos 2 caracteres")
    return null
}

/** Teléfono opcional: si está vacío, no se valida. Si viene, 8–12 dígitos. */
fun validatePhoneOptional(field: String, value: String?): FieldError? {
    val v = value?.trim().orEmpty()
    if (v.isEmpty()) return null
    val digits = v.filter(Char::isDigit)
    return if (digits.length !in 8..12)
        FieldError(field, "El teléfono debe tener entre 8 y 12 dígitos")
    else null
}

/* ─────────────── Casos de uso: Login y Registro ─────────────── */

fun validateLogin(
    email: String?,
    password: String?
): ValidationResult {
    val errs = buildList {
        validateEmail("email", email)?.let(::add)
        validatePassword("password", password)?.let(::add)
    }
    return ValidationResult(ok = errs.isEmpty(), errors = errs)
}

fun validateRegister(
    nombre: String?,
    apellido: String?,
    email: String?,
    password: String?,
    confirmar: String?,
    telefono: String?,
    aceptaTerminos: Boolean = true
): ValidationResult {
    val errs = buildList {
        validateName("nombre", nombre, "nombre")?.let(::add)
        // Si quieres apellido obligatorio, descomenta:
        // validateName("apellido", apellido, "apellido")?.let(::add)

        validateEmail("email", email)?.let(::add)
        validatePassword("password", password)?.let(::add)
        validateConfirmPassword("confirmar", password, confirmar)?.let(::add)
        validatePhoneOptional("telefono", telefono)?.let(::add)

        if (!aceptaTerminos) add(FieldError("aceptaTerminos", "Debes aceptar los Términos y Condiciones"))
    }
    return ValidationResult(ok = errs.isEmpty(), errors = errs)
}

/* ─────────────── Helper opcional ─────────────── */
fun ValidationResult.toFieldMap(): Map<String, String> =
    errors.associate { it.field to it.message }
